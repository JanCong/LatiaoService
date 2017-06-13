package com.izanpin.service.impl;

import com.izanpin.dto.OpenIMUserDto;
import com.izanpin.entity.User;
import com.izanpin.service.BaichuanService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.request.OpenimUsersUpdateRequest;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersGetResponse;
import com.taobao.api.response.OpenimUsersUpdateResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengyuancong on 2017/6/3.
 */
@Service
@Transactional
public class BaichuanServiceImpl implements BaichuanService {
    String url = "http://gw.api.taobao.com/router/rest";
    String appkey = "23872966";
    String secret = "120944c727508f61b59688a36afcdcdc";

    static Logger logger = LogManager.getLogger();

    @Override
    public void addOrUpdateOpenIMUsers(List<Userinfos> userinfoss) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        OpenimUsersAddRequest req = new OpenimUsersAddRequest();
        req.setUserinfos(userinfoss);
        OpenimUsersAddResponse rsp = client.execute(req);

        if (rsp.getFailMsg() != null && rsp.getFailMsg().contains("data exist")) {
            OpenimUsersUpdateRequest reqUpdate = new OpenimUsersUpdateRequest();
            reqUpdate.setUserinfos(userinfoss);
            OpenimUsersUpdateResponse rspUpdate = client.execute(reqUpdate);

            logger.info(rspUpdate.getBody());
        }
    }

    @Override
    public void addOrUpdateOpenIMUser(Userinfos userinfos) throws ApiException {
        List<Userinfos> list = new ArrayList<Userinfos>();
        list.add(userinfos);
        this.addOrUpdateOpenIMUsers(list);
    }

    @Override
    public void addOrUpdateOpenIMUser(User user) throws ApiException {
        Userinfos userinfos = new Userinfos();
        userinfos.setUserid(user.getId().toString());
        userinfos.setNick(user.getNickname());
        if (user.getPassword() == null) {
            userinfos.setPassword("12345");
        } else {
            userinfos.setPassword(user.getPassword().substring(0, 5));
        }
        userinfos.setName(user.getNickname());
        userinfos.setIconUrl(user.getAvatar());
        this.addOrUpdateOpenIMUser(userinfos);
    }
}

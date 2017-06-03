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
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersGetResponse;
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


    @Override
    public void addOpenIMUsers(List<Userinfos> userinfoss) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        OpenimUsersAddRequest req = new OpenimUsersAddRequest();
        req.setUserinfos(userinfoss);
        OpenimUsersAddResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
    }

    @Override
    public void addOpenIMUser(Userinfos userinfos) throws ApiException {
        List<Userinfos> list = new ArrayList<Userinfos>();
        list.add(userinfos);
        this.addOpenIMUsers(list);
    }

    @Override
    public void addOpenIMUser(User user) throws ApiException {
        Userinfos userinfos = new Userinfos();
        userinfos.setUserid(user.getId().toString());
        userinfos.setNick(user.getNickname());
        userinfos.setPassword(user.getPassword().substring(0, 5));
        this.addOpenIMUser(userinfos);
    }
}

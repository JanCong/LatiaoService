package com.izanpin.service;

import com.izanpin.entity.User;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Userinfos;

import java.util.List;

/**
 * Created by pengyuancong on 2017/6/3.
 */
public interface BaichuanService {
    void addOrUpdateOpenIMUsers(List<Userinfos> userinfoss) throws ApiException;

    void addOrUpdateOpenIMUser(Userinfos userinfos) throws ApiException;

    void addOrUpdateOpenIMUser(User user) throws ApiException;
}

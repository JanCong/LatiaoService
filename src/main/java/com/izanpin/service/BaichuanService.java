package com.izanpin.service;

import com.izanpin.entity.User;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Userinfos;

import java.util.List;

/**
 * Created by pengyuancong on 2017/6/3.
 */
public interface BaichuanService {
    void addOpenIMUsers(List<Userinfos> userinfoss) throws ApiException;

    void addOpenIMUser(Userinfos userinfos) throws ApiException;

    void addOpenIMUser(User user) throws ApiException;
}

package com.izanpin.service.impl;

import com.izanpin.common.util.StringEncrypt;
import com.izanpin.dto.LoginDto;
import com.izanpin.dto.OAuthLoginDto;
import com.izanpin.dto.SmsLoginDto;
import com.izanpin.entity.*;
import com.izanpin.enums.Gender;
import com.izanpin.enums.UserFriendStatus;
import com.izanpin.enums.UserType;
import com.izanpin.repository.UserFriendRepository;
import com.izanpin.repository.UserOAuthRepository;
import com.izanpin.repository.UserRepository;
import com.izanpin.service.*;
import com.taobao.api.domain.Userinfos;
import org.omg.CORBA.FREE_MEM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by Smart on 2017/1/30.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserTokenService userTokenService;
    @Autowired
    SmsService smsService;
    @Autowired
    ImageService imageService;
    @Autowired
    BaichuanService baichuanService;
    @Autowired
    UserOAuthRepository userOAuthRepository;
    @Autowired
    UserFriendRepository userFriendRepository;

    @Override
    public List<User> getRobotUsers() {
        return userRepository.getUsersByType(UserType.ROBOT.getValue());
    }

    @Override
    public User getUser(Long id) {
        return userRepository.selectByPrimaryKey(id);
    }

    @Override
    public void addUser(User user) throws Exception {
        if (user.getId() == null) {
            throw new Exception("userId为空哦");
        }
        userRepository.insert(user);
        baichuanService.addOrUpdateOpenIMUser(user);
    }

    @Override
    public void updateNickNmae(Long id, String nickname) throws Exception {
        if (id == null) {
            throw new Exception("id为空");
        }
        if (nickname.isEmpty()) {
            throw new Exception("昵称不能为空哦");
        }

        User user = this.getUser(id);
        user.setNickname(nickname);
        userRepository.updateByPrimaryKeySelective(user);
        baichuanService.addOrUpdateOpenIMUser(user);
    }

    @Override
    public void updatePassword(Long id, String password) throws Exception {
        if (id == null) {
            throw new Exception("id为空");
        }
        if (password.isEmpty()) {
            throw new Exception("密码不能为空哦");
        }

        User user = this.getUser(id);
        user.setPassword(StringEncrypt.Encrypt(password));
        userRepository.updateByPrimaryKeySelective(user);
        baichuanService.addOrUpdateOpenIMUser(user);
    }

    @Override
    public void updateAvatar(Long id, MultipartFile imageFile) throws Exception {
        if (id == null) {
            throw new Exception("id为空");
        }
        if (imageFile.isEmpty()) {
            throw new Exception("头像不能为空哦");
        }

        Image image = imageService.addImage(imageFile);

        User user = this.getUser(id);
        user.setAvatar(image.getUrl());
        userRepository.updateByPrimaryKeySelective(user);
        baichuanService.addOrUpdateOpenIMUser(user);
    }

    @Override
    public UserToken login(LoginDto dto) throws Exception {
        if (dto == null) {
            throw new Exception("参数错误");
        }
        if (dto.getPhone() == null || dto.getPhone().isEmpty()) {
            throw new Exception("手机号不能为空");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new Exception("密码不能为空");
        }

        String encryptedPassword = StringEncrypt.Encrypt(dto.getPassword());
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            throw new Exception("参数错误");
        }

        User user = userRepository.getUserByPhone(dto.getPhone());
        if (user == null) {
            throw new Exception("用户不存在");
        }

        if (encryptedPassword.equalsIgnoreCase(user.getPassword())) {
            //登录成功
            baichuanService.addOrUpdateOpenIMUser(user);
            return userTokenService.getUserTokenByUserId(user.getId());
        } else {
            throw new Exception("手机号或密码错误");
        }
    }

    @Override
    public UserToken smsLogin(SmsLoginDto dto) throws Exception {
        if (dto == null) {
            throw new Exception("参数错误");
        }
        if (dto.getPhone() == null || dto.getPhone().isEmpty()) {
            throw new Exception("手机号不能为空");
        }
        if (dto.getCode() == null || dto.getCode().isEmpty()) {
            throw new Exception("验证码不能为空");
        }

        if (!smsService.verifyLoginSecurityCode(dto.getPhone(), dto.getCode())) {
            throw new Exception("验证码错误");
        } else {
            User user = userRepository.getUserByPhone(dto.getPhone());
            if (user == null) {
                user = new User("辣油" + dto.getPhone().substring(dto.getPhone().length() - 4, dto.getPhone().length()),
                        dto.getPhone(), null, StringEncrypt.Encrypt(new Date().toString()), Gender.UNKNOWN.getValue(),
                        "http://storage.izanpin.com/1024.png", UserType.NORMAL.getValue());
                this.addUser(user);
                //登录成功
                baichuanService.addOrUpdateOpenIMUser(user);
                return userTokenService.getUserTokenByUserId(user.getId());
            } else {
                //登录成功
                baichuanService.addOrUpdateOpenIMUser(user);
                return userTokenService.getUserTokenByUserId(user.getId());
            }
        }
    }

    @Override
    public UserToken oauthLogin(OAuthLoginDto dto) throws Exception {
        if (dto == null) {
            throw new Exception("参数错误");
        }
        if (dto.getOpenId() == null || dto.getOpenId().isEmpty()) {
            throw new Exception("openId不能为空");
        }
        if (dto.getPlatformType() == null) {
            throw new Exception("平台类型不能为空");
        }

        UserOAuth userOAuth = userOAuthRepository.getByOpenId(dto.getOpenId());

        if (userOAuth == null) {
            User user = new User(dto.getNickname(), null, null, StringEncrypt.Encrypt(new Date().toString()),
                    dto.getGender().getValue(), dto.getIconUrl(), UserType.NORMAL.getValue());
            this.addUser(user);
            userOAuth = new UserOAuth(user.getId(), dto.getOpenId(), dto.getPlatformType().getValue());
            userOAuthRepository.add(userOAuth);
            //登录成功
            baichuanService.addOrUpdateOpenIMUser(user);
            return userTokenService.getUserTokenByUserId(user.getId());
        } else {
            //登录成功
            User user = this.getUser(userOAuth.getUserId());
            baichuanService.addOrUpdateOpenIMUser(user);
            return userTokenService.getUserTokenByUserId(userOAuth.getUserId());
        }
    }

    @Override
    public void addFriend(Long userId, Long friendId, String remark) throws Exception {
        if (userId == null) {
            throw new Exception("userId为空");
        }
        if (friendId == null) {
            throw new Exception("friendId为空");
        }

        if (userFriendRepository.get(userId, friendId) != null) {
            throw new Exception("已经添加过好友啦");
        }

        userFriendRepository.add(new UserFriend(userId, friendId, UserFriendStatus.UNVERIFIED.getValue(), remark));
    }

    @Override
    public void acceptFriend(Long userId, Long friendId) throws Exception {
        if (userId == null) {
            throw new Exception("userId为空");
        }
        if (friendId == null) {
            throw new Exception("friendId为空");
        }

        if (userFriendRepository.get(userId, friendId) != null) {
            throw new Exception("已经添加过好友啦");
        }

        userFriendRepository.add(new UserFriend(userId, friendId, UserFriendStatus.NORMAL.getValue(), null));
        userFriendRepository.updateStatus(friendId, userId, UserFriendStatus.NORMAL.getValue());
    }

    @Override
    public List<User> getFriends(Long userId) throws Exception {
        if (userId == null) {
            throw new Exception("userId为空");
        }

        return userFriendRepository.getList(userId);
    }
}

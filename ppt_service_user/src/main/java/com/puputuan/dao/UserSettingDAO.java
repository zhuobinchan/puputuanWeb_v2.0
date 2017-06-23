package com.puputuan.dao;

import com.puputuan.basedao.BaseDAO;
import com.puputuan.model.UserSetting;
import org.springframework.stereotype.Repository;

/**
 * Created by Simon on 2016/8/3.
 */
@Repository
public interface UserSettingDAO extends BaseDAO<UserSetting, Long> {

}

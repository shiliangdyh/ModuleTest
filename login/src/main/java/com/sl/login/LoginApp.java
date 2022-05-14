package com.sl.login;


import com.sl.base.BaseApplication;
import com.sl.componentbase.ServiceFactory;
import com.sl.login.service.LoginServiceIml;

public class LoginApp extends BaseApplication {

    @Override
    public void initModuleApp(BaseApplication application) {
        ServiceFactory.getInstance().setAccountService(new LoginServiceIml());
    }

    @Override
    public void initModuleData(BaseApplication application) {

    }
}

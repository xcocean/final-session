package top.lingkang.exampleservlet.config;

import top.lingkang.sessioncore.config.FinalSessionConfigurerAdapter;
import top.lingkang.sessioncore.config.FinalSessionProperties;


/**
 * @author lingkang
 * Created by 2022/1/26
 */
public class MyFinalSessionConfig extends FinalSessionConfigurerAdapter {
    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 对项目进行配置
    }
}

package xxx.elearndemo.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.Collections;

public class MybatisPlusGenerator {
    public static void main(String[] args) {
//        fastAutoGenerate();
        autoGenerate();
    }


    private static void autoGenerate() {

        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8", "root", "123456")
                .build();
        //要用AutoGenerator,用FastAutoGenerator好像可配置选项会减少
        AutoGenerator generator = new AutoGenerator(dataSourceConfig);

        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .fileOverride()// 覆盖已生成文件
                .outputDir("S:\\File Data\\Desktop\\generation")
                .build();
        generator.global(globalConfig);

        StrategyConfig strategyConfig = new StrategyConfig.Builder()  //new config.builder
                .addInclude("item_images") //配置
                .addInclude("items")
                .entityBuilder()
                .enableLombok()
                .build();  //build
        generator.strategy(strategyConfig);//配置放入generator

        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent("xxx")
                .moduleName("elearndemo")
                .entity("pojo")
                .mapper("dao")
                .pathInfo(Collections.singletonMap(OutputFile.xml, "S:\\File Data\\Desktop\\generation"))
                .build();
        generator.packageInfo(packageConfig);


        generator.execute();
    }

    private static void fastAutoGenerate() {
        FastAutoGenerator
                .create("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("xxx")
                            .fileOverride()// 覆盖已生成文件
                            .outputDir("S://generation");
                })
                .packageConfig(builder -> {//生成的文件夹名
                    builder.parent("com.xxx")
                            .moduleName("clickgrid")
                            .entity("pojo")
                            .mapper("dao")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "S://generation"));
                })
                .strategyConfig(builder -> {
                    builder
                            .addInclude("subject")
                            .addInclude("ep_mark_anime")
                            .addInclude("ep_mark_anime_movie")
                            .addInclude("episode_anime")
                            .addInclude("subject_anime")
                            .addInclude("subject_mark_anime")
                            .addInclude("tag_anime")
                            .addInclude("user").entityBuilder().enableLombok();
                })
                .execute();

    }
}

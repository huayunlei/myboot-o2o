package com.ihomefnt.o2o.intf.domain.program.dto;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 新版方案首页环境变量统一使用nacos
 *
 * @author liyonggang
 * @create 2019-07-01 17:47
 */
@Component
@Data
public class ProgramTextBean {

    /**
     * 封面页
     */
    @NacosValue(value = "${program.detail.coverWelcomeText}", autoRefreshed = true)
    private String coverWelcomeText;//封面欢迎语


    /**
     * 人物定位页
     * <p>
     * 图片说明
     * 学龄前儿童OR青少年期+与老人同住 --> 三代同堂 A
     * 学龄前儿童OR青少年期 --> 三口之家  B
     * 还没有小孩 --> 年轻夫妻  C
     */

    @NacosValue(value = "${program.detail.personLocationImageForA}", autoRefreshed = true)
    private String personLocationImageForA;//图片-->三代同堂

    @NacosValue(value = "${program.detail.personLocationImageForB}", autoRefreshed = true)
    private String personLocationImageForB;//图片-->三口之家

    @NacosValue(value = "${program.detail.personLocationImageForC}", autoRefreshed = true)
    private String personLocationImageForC;//图片-->年轻夫妻

    @NacosValue(value = "${program.detail.familyMember}", autoRefreshed = true)
    private String familyMember;//家庭成员标题

    @NacosValue(value = "${program.detail.styleLocation}", autoRefreshed = true)
    private String styleLocation;//风格定位标题

    @NacosValue(value = "${program.detail.houseStatus}", autoRefreshed = true)
    private String houseStatus;//房屋状况文案


    /**
     * 设计说明页
     */

    //美式 风格id 4
    @NacosValue(value = "${program.detail.designDescTitleForAmerican}", autoRefreshed = true)
    private String designDescTitleForAmerican;//标题文案

    @NacosValue(value = "${program.detail.designDescTextForAmerican}", autoRefreshed = true)
    private String designDescTextForAmerican;//描述

    @NacosValue(value = "${program.detail.designDescImageForAmerican}", autoRefreshed = true)
    private String designDescImageForAmerican;//图片


    //现代 风格id 5
    @NacosValue(value = "${program.detail.designDescTitleForModern}", autoRefreshed = true)
    private String designDescTitleForModern;//标题文案

    @NacosValue(value = "${program.detail.designDescTextForModern}", autoRefreshed = true)
    private String designDescTextForModern;//描述

    @NacosValue(value = "${program.detail.designDescImageForModern}", autoRefreshed = true)
    private String designDescImageForModern;//图片


    //欧式 风格id 6
    @NacosValue(value = "${program.detail.designDescTitleForEuropean}", autoRefreshed = true)
    private String designDescTitleForEuropean;//标题文案

    @NacosValue(value = "${program.detail.designDescTextForEuropean}", autoRefreshed = true)
    private String designDescTextForEuropean;//描述

    @NacosValue(value = "${program.detail.designDescImageForEuropean}", autoRefreshed = true)
    private String designDescImageForEuropean;//图片


    //中式 风格id 7
    @NacosValue(value = "${program.detail.designDescTitleForChinese}", autoRefreshed = true)
    private String designDescTitleForChinese;//标题文案

    @NacosValue(value = "${program.detail.designDescTextForChinese}", autoRefreshed = true)
    private String designDescTextForChinese;//描述

    @NacosValue(value = "${program.detail.designDescImageForChinese}", autoRefreshed = true)
    private String designDescImageForChinese;//图片


    /**
     * 材质分析图片
     */
    @NacosValue(value = "${program.detail.textureAnalyzeForAmerican}", autoRefreshed = true)
    private String textureAnalyzeForAmerican;//美式

    @NacosValue(value = "${program.detail.textureAnalyzeForModern}", autoRefreshed = true)
    private String textureAnalyzeForModern;//现代

    @NacosValue(value = "${program.detail.textureAnalyzeForEuropean}", autoRefreshed = true)
    private String textureAnalyzeForEuropean;//欧式

    @NacosValue(value = "${program.detail.textureAnalyzeForChinese}", autoRefreshed = true)
    private String textureAnalyzeForChinese;//中式

    /**
     * 平面布局
     */
    //户型信息文案
    @NacosValue(value = "${program.detail.houseTypeInfoTitle}", autoRefreshed = true)
    private String houseTypeInfoTitle;

    //项目面积
    @NacosValue(value = "${program.detail.projectSizeTitle}", autoRefreshed = true)
    private String projectSizeTitle;

    //空间格局
    @NacosValue(value = "${program.detail.spatialPatternTitle}", autoRefreshed = true)
    private String spatialPatternTitle;


    @NacosValue(value = "${program.brand.backgroundImage}", autoRefreshed = true)
    private String programBrandBackgroundImage;


    @NacosValue(value = "${program.detail.topBrandName}", autoRefreshed = true)
    private String topBrandName;


    /**
     * 方案概述
     */

    //默认方案概述描述
    @NacosValue(value = "${program.detail.solutionIdea.default.desc}", autoRefreshed = true)
    private String solutionIdeaDefaultDesc;

    //默认方案概述图片
    @NacosValue(value = "${program.detail.solutionIdea.default.images}", autoRefreshed = true)
    private String solutionIdeaDefaultImages;


    /**
     * 方案详情页面文案数据
     */
    //没有拆改过的户型格局文案
    @NacosValue(value = "${program.detail.info.houseType.Layout.noChange}",autoRefreshed = true)
    private String programHouseLayoutNoChange;

    //拆改过的户型格局文案
    @NacosValue(value = "${program.detail.info.houseType.Layout.hasChange}",autoRefreshed = true)
    private String programHouseLayoutHasChange;

}

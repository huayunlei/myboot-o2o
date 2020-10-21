package com.ihomefnt.o2o.intf.manager.constant;

/**
 * rediskey
 *
 * @author liyonggang
 * @create 2019-02-21 17:07
 */
public interface RedisKey {
    /**
     * 个人中心
     */
    class PersonalCenter {

        /**
         * 个人中心资源根据类目分组的数据
         */
        public static final String PERSONAL_CENTER_RESOURCE_GROUPBY_CATEGORY = "PERSONAL_CENTER_RESOURCE_GROUPBY_CATEGORY";

        /**
         * 个人中心资源根据KEY分组的数据
         */
        public static final String PERSONAL_CENTER_RESOURCE_GROUPBY_KEY = "PERSONAL_CENTER_RESOURCE_GROUPBY_KEY";

        /**
         * 个人中心未登录时的数据的key
         */
        public static final String PERSONAL_CENTER_DATA_NO_LOGIN = "PERSONAL_CENTER_DATA_NO_LOGIN_";

        /**
         * 默认头像地址
         */
        public static final String DEFAULT_AVATAR_IMAGE = "DEFAULT_AVATAR_IMAGE";

        /**
         * 置家顾问手机号码key
         */
        public static final String ADVISER_MOBILES = "ADVISER_MOBILES";

    }

    class DnaVote {

        /**
         * 用户投票进行中标示
         */
        public static final String DNA_VOTE_USER_STARTED = "DNA_VOTE_USER_STARTED_";

        /**
         * dna开始投票
         */
        public static final String DNA_VOTE_DNA_STARTED = "DNA_VOTE_DNA_STARTED_";

        /**
         * 用户资格缓存
         */
        public static final String XISHE_DNA_VOTE_QUALIFICATION = "xiShe_dna_vote_qualification:";
    }

    class OrderSolution {

        /**
         * 一键替换记录
         */
        public static final String APP_ONCE_REPLACE_KEY = "APP_ONCE_REPLACE_KEY:";
    }

    class LifePlate{
        /**
         * 生活板块首页晒家评论缓存
         */
        public static final String LIFE_HOME_PAGE_SHARE_ORDER_CACHE = "LIFE_HOME_PAGE_SHARE_ORDER_CACHE";

        /**
         * 生活板块首页缓存
         */
        public static final String LIFE_HOME_PAGE_DATA_CACHE = "LIFE_HOME_PAGE_DATA_CACHE";
    }
}

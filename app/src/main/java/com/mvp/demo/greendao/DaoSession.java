package com.mvp.demo.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.mvp.demo.entity.ChannelBean.NewsChannelBean;
import com.mvp.demo.entity.MediaChannel.MediaChannelBean;

import com.mvp.demo.greendao.NewsChannelBeanDao;
import com.mvp.demo.greendao.MediaChannelBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig newsChannelBeanDaoConfig;
    private final DaoConfig mediaChannelBeanDaoConfig;

    private final NewsChannelBeanDao newsChannelBeanDao;
    private final MediaChannelBeanDao mediaChannelBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        newsChannelBeanDaoConfig = daoConfigMap.get(NewsChannelBeanDao.class).clone();
        newsChannelBeanDaoConfig.initIdentityScope(type);

        mediaChannelBeanDaoConfig = daoConfigMap.get(MediaChannelBeanDao.class).clone();
        mediaChannelBeanDaoConfig.initIdentityScope(type);

        newsChannelBeanDao = new NewsChannelBeanDao(newsChannelBeanDaoConfig, this);
        mediaChannelBeanDao = new MediaChannelBeanDao(mediaChannelBeanDaoConfig, this);

        registerDao(NewsChannelBean.class, newsChannelBeanDao);
        registerDao(MediaChannelBean.class, mediaChannelBeanDao);
    }
    
    public void clear() {
        newsChannelBeanDaoConfig.clearIdentityScope();
        mediaChannelBeanDaoConfig.clearIdentityScope();
    }

    public NewsChannelBeanDao getNewsChannelBeanDao() {
        return newsChannelBeanDao;
    }

    public MediaChannelBeanDao getMediaChannelBeanDao() {
        return mediaChannelBeanDao;
    }

}
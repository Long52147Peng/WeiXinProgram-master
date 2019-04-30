package com.cjr.WechatMessage.service.Impl;

import com.cjr.WechatMessage.dao.BlinddatePostDao;
import com.cjr.WechatMessage.dao.EmploymentPostDao;
import com.cjr.WechatMessage.dao.TransactionPostDao;
import com.cjr.WechatMessage.entity.Post;
import com.cjr.WechatMessage.entity.User;
import com.cjr.WechatMessage.service.PostService;

import com.cjr.WechatMessage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Created with qml
 * @author:qml
 * @Date:2019/4/27
 * @Time:16:53
 */
@Service("postService")
public class PostServiceImpl implements PostService {

    @Autowired
    private BlinddatePostDao blinddatePostDao;

    @Autowired
    private EmploymentPostDao employmentPostDao;

    @Autowired
    private TransactionPostDao transactionPostDao;

    @Autowired
    private UserService userService;

    private int postNumOfPage = 5;

    public Post selectByPostId(String postId) {
        return null;
    }

    public List<Post> selectByUserId(String userId) {
        return null;
    }

    /**
     * 查询感兴趣的帖子，并返回
     * @param openId
     * @param index
     * @return
     */
    public List<Post> selectLike(String openId, int index) {
        List<Post> posts = null;
        User user = userService.getByOpenId(openId);
        int model = user.getChoiceMode();
        switch (model) {
            case 0:
                posts = null;
                break;
            case 1:
                posts = selectAll(1, index);
                break;
            case 2:
                posts = selectAll(2, index);
                break;
            case 3:
                posts = selectAll(3, index);
                break;
            case 12:
                posts = selectAll(1, index);
                posts.addAll(selectAll(2, index));
                break;
            case 13:
                posts = selectAll(1, index);
                posts.addAll(selectAll(3, index));
                break;
            case 23:
                posts = selectAll(2, index);
                posts.addAll(selectAll(3, index));
                break;
            case 123:
                posts = selectAll(1, index);
                posts.addAll(selectAll(2, index));
                posts.addAll(selectAll(3, index));
                break;
            default:
                break;
        }
        if (index * postNumOfPage >= posts.size()) {
            return null;
        }
        return posts.subList(index * postNumOfPage, (index+1)*postNumOfPage>=posts.size() ? posts.size()-1 : (index+1)*postNumOfPage);
    }

    /**
     * 查询某一类型的帖子
     * @param type
     * @param index
     * @return
     */
    public List<Post> selectAll(int type, int index) {
        List<Post> posts = null;
        switch (type){
            case 1:
                posts = blinddatePostDao.selectAll();
                break;
            case 2:
                posts = employmentPostDao.selectAll();
                break;
            case 3:
                posts = transactionPostDao.selectAll();
                break;
            default:
                break;
        }
        if (index * postNumOfPage >= posts.size()) {
            return null;
        }
        return posts.subList(index * postNumOfPage, (index+1)*postNumOfPage>=posts.size() ? posts.size()-1 : (index+1)*postNumOfPage);
    }
}

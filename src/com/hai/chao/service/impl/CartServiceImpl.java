package com.hai.chao.service.impl;

import com.hai.chao.dao.CartDao;
import com.hai.chao.dao.GoodDao;
import com.hai.chao.dao.impl.CartDaoImpl;
import com.hai.chao.dao.impl.GoodDaoImpl;
import com.hai.chao.domain.Cart;
import com.hai.chao.domain.Good;
import com.hai.chao.service.CartService;
import org.apache.commons.collections.CollectionUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * @program: estore
 * @description: 购物车相关操作
 * @author: xiaohai
 * @create: 2018-09-01 22:58
 **/
public class CartServiceImpl implements CartService {
    @Override
    public int addToCard(int uid, Integer gid) throws SQLException {
        //根据uid和gid查询商品是否已经存在：存在buynum+1,修改；不存在，插入buynum=1
        CartDao cartDao = new CartDaoImpl();
        Cart c = cartDao.selectCartByUidAndGid(uid,gid);

        if(c== null){
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setGid(gid);
            cart.setBuynum(1);
            return cartDao.insert(cart);
        }else{
            c.setBuynum(c.getBuynum()+1);
            return cartDao.updateBuynumByPrimary(c);
        }
    }

    /**
     * 查询用户购物车中所有商品
     * @Param: [uid]:用户id，
     * @return: java.util.List<com.hai.chao.domain.Cart>
     */
    @Override
    public List<Cart> selectCartListByUid(int uid) throws SQLException {
        CartDao cartDao = new CartDaoImpl();
        //先根据uid查询Cart表
        List<Cart> carts = cartDao.selectCartListByUid(uid);
        if(CollectionUtils.isEmpty(carts)){
            return null;
        }
        //遍历，根据gid查询good表,放进cart对象中
        GoodDao goodDao = new GoodDaoImpl();
        for (Cart cart : carts){
            int gid = cart.getGid();
            Good good = goodDao.selectGoodByPrimaryId(String.valueOf(gid));
            cart.setGood(good);
        }

        return carts;
    }

   /**
    * 根据用户id和商品id个性购物车中对应数量
    * @Param: [uid, i]
    * @return: int
    */
    @Override
    public int updateCartNumByUidAndGid(int uid, int gid,int buynum) throws SQLException {
        CartDao cartDao = new CartDaoImpl();

        return cartDao.updateCartNumByUidAndGid(uid,gid,buynum);
    }

   /**
    * 根据uid和gid删除购物车
    * @Param: [uid, gid]
    * @return: int
    */
    @Override
    public int deleteCartByUidAndGid(int uid, int gid) throws SQLException {
        CartDao cartDao = new CartDaoImpl();

        return cartDao.deleteCartByUidAndGid(uid,gid);
    }
}

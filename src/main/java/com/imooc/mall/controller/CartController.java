package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.filter.UserFilter;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车Controller
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @ApiOperation("获取购物车列表")
    @GetMapping("/list")
    public ApiRestResponse list() {
        //内部获取用户ID,防止横向越权
        List<CartVO> list = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(list);
    }

    @ApiOperation("添加商品到购物车")
    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId,
                               @RequestParam Integer count) {
        List<CartVO> list = cartService.add(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(list);
    }

    @ApiOperation("更新购物车")
    @PostMapping("/update")
    public ApiRestResponse update(@RequestParam Integer productId,
                                  @RequestParam Integer count) {
        List<CartVO> list = cartService.update(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(list);
    }

    @ApiOperation("删除购物车")
    @PostMapping("/delete")
    public ApiRestResponse delete(@RequestParam Integer productId) {
        //不能传入userId和cartId，否则可以删除别人的购物车
        List<CartVO> list = cartService.delete(UserFilter.currentUser.getId(), productId);
        return ApiRestResponse.success(list);
    }

    @ApiOperation(("购物车单选/不选某个商品"))
    @PostMapping("/select")
    public ApiRestResponse select(@RequestParam Integer productId,
                                  @RequestParam Integer selected) {
        if (!validSelected(selected)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CART_STATUS_ERROR);
        }
        List<CartVO> list = cartService.selectOrNot(UserFilter.currentUser.getId(), productId, selected);
        return ApiRestResponse.success(list);
    }

    @ApiOperation(("购物车全选/全不选商品"))
    @PostMapping("/selectAll")
    public ApiRestResponse selectAll(@RequestParam Integer selected) {
//        if (!validSelected(selected)) {
//            throw new ImoocMallException(ImoocMallExceptionEnum.CART_STATUS_ERROR);
//        }
        List<CartVO> list = cartService.selectAllOrNot(UserFilter.currentUser.getId(), selected);
        return ApiRestResponse.success(list);
    }

    private boolean validSelected(Integer selected) {
        if (selected!=Constant.Cart.CHECKED || selected!= Constant.Cart.UN_CHECKED) {
            return false;
        }
        return true;
    }
}

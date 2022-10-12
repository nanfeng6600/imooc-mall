package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商品后台管理 Controller
 */
@RestController
public class OrderAdminController {

    @Resource
    private OrderService orderService;

    @ApiOperation("管理员订单列表")
    @GetMapping("/admin/order/list")
    public ApiRestResponse list(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("管理员发货")
    @PostMapping("/admin/order/delivered")
    public ApiRestResponse delivered(@RequestParam String orderNo) {
        orderService.deliver(orderNo);
        return ApiRestResponse.success();
    }
    // 0用户已取消，10未支付，20已支付，30已发货，40交易完成
    @ApiOperation("管理员发货")
    @PostMapping("/order/finish")
    public ApiRestResponse finish(@RequestParam String orderNo) {
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }
}

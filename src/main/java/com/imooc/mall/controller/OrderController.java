package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.OrderVO;
import com.imooc.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 订单 Controller
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @ApiOperation("创建订单")
    @PostMapping("/create")
    public ApiRestResponse create(@Valid @RequestBody CreateOrderReq createOrderReq) {
        String oderNo = orderService.create(createOrderReq);
        return ApiRestResponse.success(oderNo);
    }

    @ApiOperation("订单详情")
    @GetMapping("/detail")
    public ApiRestResponse detail(@RequestParam String orderNo) {
        OrderVO orderVO = orderService.detail(orderNo);
        return ApiRestResponse.success(orderVO);
    }

    @ApiOperation("订单列表")
    @GetMapping("/list")
    public ApiRestResponse list(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        PageInfo pageInfo = orderService.listForCustomer(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("取消订单")
    @PostMapping("/cancel")
    public ApiRestResponse cancel(@RequestParam String orderNo) {
        orderService.cancel(orderNo);
        return ApiRestResponse.success();
    }

    @ApiOperation("生成支付二维码")
    @PostMapping("/qrcode")
    public ApiRestResponse qrcode(@RequestParam String orderNo) {
        String pngAddress = orderService.qrcode(orderNo);
        return ApiRestResponse.success(pngAddress);
    }

    @ApiOperation("支付订单")
    @GetMapping("/pay")
    public ApiRestResponse pay(@RequestParam String orderNo) {
        orderService.pay(orderNo);
        return ApiRestResponse.success();
    }
}

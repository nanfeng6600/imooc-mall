package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.UpdateProductReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * 后台商品管理
 */
@RestController
public class ProductAdminController {

    @Resource
    private ProductService productService;

    @ApiOperation("后台添加商品")
    @PostMapping("/admin/product/add")
    public ApiRestResponse addProduct(@RequestBody @Valid AddProductReq addProductReq) {
        productService.add(addProductReq);
        return ApiRestResponse.success();
    }

    @PostMapping("/admin/upload/file")
    public ApiRestResponse upload(@RequestParam("file") MultipartFile file,
                                  HttpServletRequest httpServletRequest) {
        //获取原始文件名
        String fileName = file.getOriginalFilename();
        //获取原始文件名后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffixName;
        //创建文件夹
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        //创建图片文件
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        //如果文件夹不存在，则创建
        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdir()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.MKDIR_FAILED);
            }
        }
        //将图片文件写入到对应路径
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/images/" + newFileName);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.UPLOAD_FAILED);
        }
    }

    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                    null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        System.out.println(effectiveURI);
        return effectiveURI;
    }

    @ApiOperation("后台更新商品")
    @PostMapping("/admin/product/update")
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq) {
        productService.update(updateProductReq);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台删除商品")
    @PostMapping("/admin/product/delete")
    public ApiRestResponse deleteProduct(@RequestParam Integer id) {
        productService.delete(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台批量上下架商品")
    @PostMapping("/admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateSellStatus(@RequestParam Integer[] ids,
                                                 @RequestParam Integer sellStatus ) {
        productService.batchUpdateSellStatus(ids,sellStatus);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台商品列表")
    @PostMapping("/admin/product/list")
    public ApiRestResponse list(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        PageInfo pageInfo = productService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
}

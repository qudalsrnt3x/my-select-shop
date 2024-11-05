package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.dto.SearchParameter;
import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ProductApiController {

    private final ProductService productService;

    // 관심 상품 등록하기
    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return productService.createProduct(requestDto, userDetails.getUser());
    }

    // 관심 상품 조회하기
    @GetMapping("/products")
    public Page<ProductResponseDto> getProducts(@ModelAttribute SearchParameter parameter,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.getProducts(userDetails.getUser(), parameter);
    }

    // 회원이 등록한 폴더 내 모든 상품 조회
    @GetMapping("/folders/{folderId}/products")
    public Page<ProductResponseDto> getProductsInFolder(@PathVariable Long folderId,
                                                        @ModelAttribute SearchParameter parameter,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.getProductsInFolder(folderId, parameter, userDetails.getUser());
    }

    // 관심 상품 희망 최저가 등록하기
    @PutMapping("/products/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id,
                                            @RequestBody ProductMypriceRequestDto requestDto) {
        // 응답 보내기
        return productService.updateProduct(id, requestDto);
    }

    // 상품에 폴더 추가
    @PostMapping("/products/{productId}/folder")
    public void addFolder(@PathVariable Long productId,
                          @RequestParam Long folderId,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        productService.addFolder(productId, folderId, userDetails.getUser());
    }

    // 관리자 조회
    @GetMapping("/admin/products")
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

}

package com.musinsa.store.product.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musinsa.store.common.dto.Page;
import com.musinsa.store.common.dto.PageResponsePayload;
import com.musinsa.store.common.dto.ResponsePayload;
import com.musinsa.store.common.exception.NotFoundException;
import com.musinsa.store.product.api.dto.BrandPayload;
import com.musinsa.store.product.domain.Brand;
import com.musinsa.store.product.domain.BrandService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Slf4j
public class BrandController {
  private final BrandService brandService;
  
  @GetMapping({"", "/"})
  public PageResponsePayload<BrandPayload> list(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "20") Integer size) {
    log.info("list brand: page({}) size({})", page, size);

    Page<Brand> brandPage = brandService.list(page, size);

    return PageResponsePayload.<BrandPayload>builder()
        .data(brandPage.stream().map(BrandPayload::of).toList())
        .page(brandPage.getPage())
        .size(brandPage.getSize())
        .totalPage(brandPage.getTotalPage())
        .build();
  }

  @PostMapping({"", "/"})
  public ResponsePayload<BrandPayload> create(@Valid @RequestBody BrandPayload brandPayload) {
    log.info("create brand: {}", brandPayload);

    Brand brand = brandService.create(brandPayload.toBrand());

    return ResponsePayload.<BrandPayload>builder()
        .data(BrandPayload.of(brand))
        .build();
  }

  @GetMapping("/{id}")
  public ResponsePayload<BrandPayload> get(@PathVariable("id") Long id) {
    log.info("get brand: {}", id);

    Brand brand = brandService.get(id)
        .orElseThrow(() -> new NotFoundException("Brand not found"));

    return ResponsePayload.<BrandPayload>builder()
        .data(BrandPayload.of(brand))
        .build();
  }

  @PostMapping("/{id}")
  public ResponsePayload<BrandPayload> update(@PathVariable("id") Long id,
      @Valid @RequestBody BrandPayload brandPayload) {
    log.info("update brand: id({}) {}", id, brandPayload);

    brandPayload.setId(id);
    Brand brand = brandService.update(brandPayload.toBrand())
        .orElseThrow(() -> new NotFoundException("Brand not found"));

    return ResponsePayload.<BrandPayload>builder()
        .data(BrandPayload.of(brand))
        .build();
  }

  @DeleteMapping("/{id}")
  public ResponsePayload<Void> delete(@PathVariable("id") Long id) {
    log.info("delete brand: id({})", id);

    brandService.delete(id);

    return ResponsePayload.SUCCESS;
  }
}

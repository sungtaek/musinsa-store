package com.musinsa.store.product.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musinsa.store.common.dto.ResponsePayload;
import com.musinsa.store.common.exception.NotFoundException;
import com.musinsa.store.product.api.dto.BrandPayload;
import com.musinsa.store.product.domain.BrandService;
import com.musinsa.store.product.domain.dto.BrandDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Slf4j
public class BrandController {
  private final BrandService brandService;
  
  @PostMapping({"", "/"})
  public ResponsePayload<BrandPayload> create(@Valid @RequestBody BrandPayload brandPayload) {
    log.info("create brand: {}", brandPayload);

    BrandDto brand = brandService.create(brandPayload.toBrand());

    return ResponsePayload.<BrandPayload>builder()
        .data(BrandPayload.from(brand))
        .build();
  }

  @GetMapping("/{id}")
  public ResponsePayload<BrandPayload> get(@PathVariable("id") Long id) {
    log.info("get brand: {}", id);

    BrandDto brand = brandService.get(id)
        .orElseThrow(() -> new NotFoundException("Brand not found"));

    return ResponsePayload.<BrandPayload>builder()
        .data(BrandPayload.from(brand))
        .build();
  }

  @PostMapping("/{id}")
  public ResponsePayload<BrandPayload> update(@PathVariable("id") Long id,
      @Valid @RequestBody BrandPayload brandPayload) {
    log.info("update brand: id({}) {}", id, brandPayload);

    brandPayload.setId(id);
    BrandDto brand = brandService.update(brandPayload.toBrand())
        .orElseThrow(() -> new NotFoundException("Brand not found"));

    return ResponsePayload.<BrandPayload>builder()
        .data(BrandPayload.from(brand))
        .build();
  }

  @DeleteMapping("/{id}")
  public ResponsePayload<Void> delete(@PathVariable("id") Long id) {
    log.info("delete brand: id({})", id);

    brandService.delete(id);

    return ResponsePayload.SUCCESS;
  }
}

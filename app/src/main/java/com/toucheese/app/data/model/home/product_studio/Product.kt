package com.toucheese.app.data.model.home.product_studio

data class Product(
    val imageUrl: String, // 상품 대표 이미지
    val name: String, // 상품명
    val description: String, // 상품 설명
    val reviewCount: Int, // 리뷰 수
    val price: Int // 가격
)

object SampleProducts {
    val products = listOf(
        com.toucheese.app.data.model.home.product_studio.Product(
            imageUrl = "https://via.placeholder.com/80",
            name = "증명사진",
            description = "신원 확인이 필요한 목적으로 사용되는 사진입니다.",
            reviewCount = 108,
            price = 75000
        ),
        com.toucheese.app.data.model.home.product_studio.Product(
            imageUrl = "https://via.placeholder.com/80",
            name = "배우 프로필",
            description = "배우나 모델의 프로필 사진 촬영에 적합한 상품입니다.",
            reviewCount = 95,
            price = 120000
        ),
        com.toucheese.app.data.model.home.product_studio.Product(
            imageUrl = "https://via.placeholder.com/80",
            name = "가족사진",
            description = "소중한 순간을 기록하기 위한 가족사진 촬영 패키지.",
            reviewCount = 68,
            price = 150000
        )
    )
}
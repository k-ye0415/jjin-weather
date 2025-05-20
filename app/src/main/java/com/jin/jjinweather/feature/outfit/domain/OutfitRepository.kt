package com.jin.jjinweather.feature.outfit.domain

/**
 * 온도를 기반으로 옷 이미지 제작
 *
 * @param temperature 적절한 옷 스타일을 정의하기 위한 온도
 * @return [Result] 생성된 이미지의 URL string 반환
 */
interface OutfitRepository {
    suspend fun generateOutfitImageUrl(temperature: Int): Result<String>
}

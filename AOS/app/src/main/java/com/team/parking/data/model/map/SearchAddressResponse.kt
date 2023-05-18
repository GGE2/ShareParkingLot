package com.team.parking.data.model.map

data class SearchAddressResponse (
    var documents: List<AddressResponse>
)

data class AddressResponse (
    var y: String,
    var x: String,
    var address: Address?,
    var road_address: RoadAddress?
)

data class Address(
    var address_name: String
)

data class RoadAddress(
    var address_name: String
)
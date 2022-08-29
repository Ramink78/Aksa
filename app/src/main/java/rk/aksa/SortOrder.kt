package rk.aksa

sealed class SortOrder {
    object DateAscending : SortOrder()
    object DateDescending : SortOrder()
    object SizeAscending : SortOrder()
    object SizeDescending : SortOrder()
}

class Node(
    val id: Int,
    val adjacentNodes: MutableSet<Node> = mutableSetOf()
)
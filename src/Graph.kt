class Graph(
    val nodes: Set<Node>
) {
    companion object {
        fun getFirstGraph(): Graph {
            val node1 = Node(1)
            val node2 = Node(2)
            val node3 = Node(3)
            val node4 = Node(4)
            val node5 = Node(5)
            val node6 = Node(6)

            node1.adjacentNodes.addAll(listOf(node5, node2))
            node2.adjacentNodes.addAll(listOf(node1, node5, node3))
            node3.adjacentNodes.addAll(listOf(node2, node4))
            node4.adjacentNodes.addAll(listOf(node6, node3, node5))
            node5.adjacentNodes.addAll(listOf(node4, node2, node1))
            node6.adjacentNodes.addAll(listOf(node4))

            return Graph(setOf(node1, node2, node3, node4, node5, node6))
        }

        fun getSecondGraph(): Graph {
            val node0 = Node(0)
            val node1 = Node(1)
            val node2 = Node(2)
            val node3 = Node(3)
            val node4 = Node(4)
            val node5 = Node(5)
            val node6 = Node(6)
            val node7 = Node(7)

            node0.adjacentNodes.addAll(listOf(node1, node2, node5, node6))
            node1.adjacentNodes.addAll(listOf(node0, node2, node6, node5, node3))
            node2.adjacentNodes.addAll(listOf(node0, node1, node5, node6, node4))
            node3.adjacentNodes.addAll(listOf(node1, node4, node6, node7))
            node4.adjacentNodes.addAll(listOf(node2, node3, node6))
            node5.adjacentNodes.addAll(listOf(node0, node1, node2, node6, node7))
            node6.adjacentNodes.addAll(listOf(node2, node0, node1, node5, node7))
            node7.adjacentNodes.addAll(listOf(node3, node5, node2, node6))

            return Graph(setOf(node0, node1, node2, node3, node4, node5, node6, node7))
        }
    }

    fun isNeighbor(nodeOne: Node, nodeTwo: Node) =
        nodeOne.adjacentNodes.contains(nodeTwo) && nodeTwo.adjacentNodes.contains(nodeOne)
}
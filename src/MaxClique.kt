import java.util.*
import kotlin.collections.HashSet

fun main(args: Array<String>) {
    val maxClique = MaxClique()
    val maximalCliques = maxClique.allMaximalCliques()
    val maximumClique = maxClique.maximumClique()

    maximalCliques.forEachIndexed { index, hashSet ->
        println("===============")
        println("| Clique: ${index + 1}")
        println("| Size: ${hashSet.size}")
        println("| Nodes:")
        hashSet.forEach { println("| - ${it.id}") }
    }

    println("=========================================")
    println("| MAXIMUM CLIQUE |")
    println("| Size: ${maximumClique.size}")
    println("| Nodes:")
    maximumClique.forEach { println("| - ${it.id}") }
    println("=========================================")
}

class MaxClique {
    private val graph = Graph.getFirstGraph()
    private val cliques = mutableSetOf<HashSet<Node>>()

    fun allMaximalCliques(): MutableSet<HashSet<Node>> {
        val potentialClique = mutableListOf<Node>()
        val candidates = mutableListOf<Node>()
        val alreadyFound = mutableListOf<Node>()
        candidates.addAll(graph.nodes)
        findCliques(potentialClique, candidates, alreadyFound)
        return cliques
    }

    fun maximumClique(): HashSet<Node> =
        cliques.toList().sortedByDescending { it.size }.first().toHashSet()

    private fun findCliques(
        potentialClique: MutableList<Node>,
        candidates: MutableList<Node>,
        alreadyFound: MutableList<Node>
    ) {
        val candidatesArray = ArrayList(candidates)
        // for each candidateNode in candidates do
        for (candidate in candidatesArray) {
            val newCandidates = ArrayList<Node>()
            val newAlreadyFound = ArrayList<Node>()

            potentialClique.add(candidate)
            candidates.remove(candidate)

            // create newCandidates by removing nodes in candidates not
            // connected to candidate node
            for (newCandidate in candidates) {
                if (graph.isNeighbor(candidate, newCandidate)) {
                    newCandidates.add(newCandidate)
                }
            }

            // create newAlreadyFound by removing nodes in alreadyFound
            // not connected to candidate node
            for (newFound in alreadyFound) {
                if (graph.isNeighbor(candidate, newFound)) {
                    newAlreadyFound.add(newFound)
                }
            }

            // if newCandidates and newAlreadyFound are empty
            if (newCandidates.isEmpty() && newAlreadyFound.isEmpty()) {
                // potentialClique is maximalClique
                cliques.add(HashSet(potentialClique))
            } else {
                // recursive call
                findCliques(
                    potentialClique,
                    newCandidates,
                    newAlreadyFound
                )
            }

            // move candidateNode from potential_clique to alreadyFound;
            alreadyFound.add(candidate)
            potentialClique.remove(candidate)
        }
    }
}


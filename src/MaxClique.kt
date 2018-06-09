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
        for (candidate in candidatesArray) {
            val newCandidates = ArrayList<Node>()
            val newAlreadyFound = ArrayList<Node>()

            makeCandidatePotential(potentialClique, candidate, candidates)
            createNewCandidates(candidates, candidate, newCandidates)
            createNewAlreadyFound(alreadyFound, candidate, newAlreadyFound)

            if (isMaximalCliqueFound(newCandidates, newAlreadyFound)) {
                cliques.add(HashSet(potentialClique))
            } else {
                findCliques(
                    potentialClique,
                    newCandidates,
                    newAlreadyFound
                )
            }
            alreadyFound.add(candidate)
            potentialClique.remove(candidate)
        }
    }

    private fun isMaximalCliqueFound(
        newCandidates: ArrayList<Node>,
        newAlreadyFound: ArrayList<Node>
    ) = newCandidates.isEmpty() && newAlreadyFound.isEmpty()

    /**
     * Removing nodes in candidates not connected to candidate node
     * to create newCandidates
     */
    private fun createNewCandidates(
        candidates: MutableList<Node>,
        candidate: Node,
        newCandidates: ArrayList<Node>
    ) {
        for (newCandidate in candidates) {
            if (graph.isNeighbor(candidate, newCandidate)) {
                newCandidates.add(newCandidate)
            }
        }
    }

    /**
     * Removing nodes in alreadyFound not connected to candidate node
     * to create newAlreadyFound
     */
    private fun createNewAlreadyFound(
        alreadyFound: MutableList<Node>,
        candidate: Node,
        newAlreadyFound: ArrayList<Node>
    ) {
        for (newFound in alreadyFound) {
            if (graph.isNeighbor(candidate, newFound)) {
                newAlreadyFound.add(newFound)
            }
        }
    }

    private fun makeCandidatePotential(
        potentialClique: MutableList<Node>,
        candidate: Node,
        candidates: MutableList<Node>
    ) {
        potentialClique.add(candidate)
        candidates.remove(candidate)
    }
}


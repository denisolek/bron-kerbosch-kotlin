import java.util.*
import kotlin.collections.HashSet

fun main(args: Array<String>) {
    val maxClique = MaxClique()
    val maximalCliques = maxClique.allMaximalCliques()
    val maximumClique = maxClique.maximumClique()

    maximalCliques.forEachIndexed { index, hashSet ->
        println("===============")
        println("| Clique: ${index+1}")
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
    private val graph = Graph.getSecondGraph()
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
        if (!end(candidates, alreadyFound)) {
            // for each candidate_node in candidates do

            for (candidate in candidatesArray) {
                val newCandidates = ArrayList<Node>()
                val newAlreadyFound = ArrayList<Node>()

                // move candidate node to potential_clique
                potentialClique.add(candidate)
                candidates.remove(candidate)

                // create new_candidates by removing nodes in candidates not
                // connected to candidate node
                for (newCandidate in candidates) {
                    if (graph.isNeighbor(candidate, newCandidate)) {
                        newCandidates.add(newCandidate)
                    }
                }

                // create new_already_found by removing nodes in already_found
                // not connected to candidate node
                for (newFound in alreadyFound) {
                    if (graph.isNeighbor(candidate, newFound)) {
                        newAlreadyFound.add(newFound)
                    }
                }

                // if new_candidates and new_already_found are empty
                if (newCandidates.isEmpty() && newAlreadyFound.isEmpty()) {
                    // potential_clique is maximal_clique
                    cliques.add(HashSet(potentialClique))
                } // of if
                else {
                    // recursive call
                    findCliques(
                        potentialClique,
                        newCandidates,
                        newAlreadyFound
                    )
                }

                // move candidate_node from potential_clique to already_found;
                alreadyFound.add(candidate)
                potentialClique.remove(candidate)
            }
        }
    }

    private fun end(candidates: List<Node>, alreadyFound: List<Node>): Boolean {
        var end = false
        var edgeCounter: Int
        for (found in alreadyFound) {
            edgeCounter = 0
            for (candidate in candidates) {
                if (graph.isNeighbor(found, candidate)) {
                    edgeCounter++
                }
            }
            if (edgeCounter == candidates.size) {
                end = true
            }
        }
        return end
    }
}


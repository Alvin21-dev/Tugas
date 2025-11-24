import java.util.*;

public class GraphDFS {
    private int vertices; // jumlah node
    private LinkedList<Integer>[] adj; // adjacency list

    public GraphDFS(int v) {
        vertices = v;
        adj = new LinkedList[v];
        for(int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    // Menambahkan edge antar node (arah satu arah)
    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    // DFS rekursif dengan pencarian target n
    public boolean DFS(int current, int target, boolean[] visited, List<Integer> path) {
        visited[current] = true;
        path.add(current);

        if(current == target) {
            return true; // target ditemukan
        }

        for(int neighbor : adj[current]) {
            if(!visited[neighbor]) {
                if(DFS(neighbor, target, visited, path)) {
                    return true;
                }
            }
        }

        // Backtracking jika target tidak ditemukan di cabang ini
        path.remove(path.size() - 1);
        return false;
    }

    public static void main(String[] args) {
        GraphDFS graph = new GraphDFS(8);

        // Misal relasi antar node (arah satu arah):
        // a1(0) -> a2(1), a3(2)
        // a2(1) -> a4(3), a5(4)
        // a3(2) -> a6(5)
        // a4(3) -> a7(6)
        // a5(4) -> a8(7)
        // a6(5) -> -
        // a7(6) -> -
        // a8(7) -> -
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 6);
        graph.addEdge(4, 7);

        int target = 7; // mencari node a8 (indeks 7)
        int startNode = 0; // mulai dari a1 (indeks 0)
        boolean[] visited = new boolean[8];
        List<Integer> path = new ArrayList<>();

        System.out.println("Proses pencarian DFS untuk mencari node a8 mulai dari a1:");

        if(graph.DFS(startNode, target, visited, path)) {
            System.out.print("Node a8 ditemukan! Jalur: ");
            for(int i = 0; i < path.size(); i++) {
                System.out.print("a" + (path.get(i)+1));
                if(i < path.size() - 1) System.out.print(" -> ");
            }
            System.out.println();
        } else {
            System.out.println("Node a8 tidak ditemukan.");
        }
    }
}

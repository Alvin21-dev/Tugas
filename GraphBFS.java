import java.util.*;

public class GraphBFS {
    private int vertices;
    private LinkedList<Integer>[] adj;

    public GraphBFS(int v) {
        vertices = v;
        adj = new LinkedList[v];
        for(int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public boolean BFS(int start, int target) {
        boolean[] visited = new boolean[vertices];
        int[] parent = new int[vertices]; // untuk mencatat jalur
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.add(start);

        while(!queue.isEmpty()) {
            int current = queue.poll();
            if(current == target) {
                // Cetak jalur dari start ke target menggunakan parent array
                printPath(parent, start, target);
                return true;
            }
            for(int neighbor : adj[current]) {
                if(!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    private void printPath(int[] parent, int start, int target) {
        List<Integer> path = new ArrayList<>();
        int crawl = target;
        path.add(crawl);
        while(parent[crawl] != -1) {
            crawl = parent[crawl];
            path.add(crawl);
        }
        Collections.reverse(path);
        System.out.print("Jalur BFS: ");
        for(int i = 0; i < path.size(); i++) {
            System.out.print("a" + (path.get(i)+1));
            if(i < path.size() - 1) System.out.print(" -> ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        GraphBFS graph = new GraphBFS(8);

        // Hubungan antar node sama seperti DFS
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 6);
        graph.addEdge(4, 7);

        int target = 7; // node a8
        int startNode = 0; // node a1

        System.out.println("Proses pencarian BFS untuk mencari node a8 mulai dari a1:");
        if(graph.BFS(startNode, target)) {
            System.out.println("Node a8 ditemukan.");
        } else {
            System.out.println("Node a8 tidak ditemukan.");
        }
    }
}

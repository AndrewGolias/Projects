class Graph:
    def __init__(self, vertices):
        # Using vertices + 1 to support 1-based indexing if needed
        self.V = vertices
        self.adj = [[] for _ in range(vertices)]

    def add_edge(self, u, v):
        # Undirected graph for connected components
        self.adj[u].append(v)
        self.adj[v].append(u)

    def _explore(self, v, compID, comp):
        """Recursive helper to label a specific component."""
        comp[v] = compID
        for neighbor in self.adj[v]:
            if comp[neighbor] == 0:
                self._explore(neighbor, compID, comp)

    def find_components(self):
        """Sets the component ID for every vertex. Returns the comp array."""
        # Initialize comp array with 0 (indicates unvisited)
        comp = [0] * self.V
        current_id = 0

        for i in range(self.V):
            if comp[i] == 0:
                # Found a new component
                current_id += 1
                self._explore(i, current_id, comp)

        return comp


g = Graph(10)  # vertices 0 to 6
g.add_edge(0, 1)
g.add_edge(0, 2)
g.add_edge(1, 3)
g.add_edge(1, 4)
g.add_edge(2, 5)
g.add_edge(2, 6)
g.add_edge(7, 8)
g.add_edge(8, 9)
comp_results = g.find_components()

for v in range(g.V):
    print(v, ": ", g.adj[v])
print(f"Comp ID: {comp_results}")

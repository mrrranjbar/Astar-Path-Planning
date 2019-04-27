import java.util.ArrayList;

public class MinHeap {
    public static ArrayList<Vertex> list;
    public static ArrayList<Vertex> ProcessedVertices;

    public MinHeap() {

        this.list = new ArrayList<Vertex>();
        this.ProcessedVertices  = new ArrayList<Vertex>();
    }

    public MinHeap(ArrayList<Vertex> items) {

        this.list = items;
        buildHeap();
    }

    public void insert(Vertex item) {
        for (Vertex ver: ProcessedVertices) {
            if(item.pt.x == ver.pt.x && item.pt.y == ver.pt.y)
                return;
        }
        for (Vertex ver: list) {
            if(item.pt.x == ver.pt.x && item.pt.y == ver.pt.y)
                return;
        }
            list.add(item);
            int i = list.size() - 1;
            int parent = parent(i);

            while (parent != i && list.get(i).f < list.get(parent).f) {

                swap(i, parent);
                i = parent;
                parent = parent(i);
            }
    }

    public void buildHeap() {

        for (int i = list.size() / 2; i >= 0; i--) {
            minHeapify(i);
        }
    }

    public Vertex extractMin() {

        if (list.size() == 0) {
            return new Vertex(new Point(0,0),0,-1,null);
        } else if (list.size() == 1) {
            Vertex min = list.remove(0);
            return min;
        }

        // remove the last item ,and set it as new root
        Vertex min = list.get(0);
        Vertex lastItem = list.remove(list.size() - 1);
        list.set(0, lastItem);

        // bubble-down until heap property is maintained
        minHeapify(0);

        // return min key
        return min;
    }

    private void minHeapify(int i) {

        int left = left(i);
        int right = right(i);
        int smallest = -1;

        // find the smallest key between current node and its children.
        if (left <= list.size() - 1 && list.get(left).f < list.get(i).f) {
            smallest = left;
        } else {
            smallest = i;
        }

        if (right <= list.size() - 1 && list.get(right).f < list.get(smallest).f) {
            smallest = right;
        }

        // if the smallest key is not the current key then bubble-down it.
        if (smallest != i) {

            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    public Vertex getMin() {

        return list.get(0);
    }

    public boolean isEmpty() {

        return list.size() == 0;
    }

    private int right(int i) {

        return 2 * i + 2;
    }

    private int left(int i) {

        return 2 * i + 1;
    }

    private int parent(int i) {

        if (i % 2 == 1) {
            return i / 2;
        }

        return (i - 1) / 2;
    }

    private void swap(int i, int parent) {
        Vertex temp = list.get(parent);
        list.set(parent, list.get(i));
        list.set(i, temp);
    }
}

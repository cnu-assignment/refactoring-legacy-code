import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestGraph {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Graph g = new Graph("graph.txt"); //그래프 g 생성
        g.prim(0);
    }

}

public class Graph {
    int size;
    int edgeSize;
    String[] graphSize;
    float [][] adjacency;
    String[] vertices;


    public class edge {
        int v,w,weight; //정점과 가중치
        boolean selected; //최소 신장 트리에 사용 되었는지 여부를 반환하기 위해서
    }

    public void add(int v, int w, int weight) {
        adjacency[v][w] = adjacency[w][v] = weight;
    }

    public Graph(String args) { //그래프 생성자 메소드
        try {
/**  Simplifying Method Calls // Rename method  **/
            edge e = new edge();
            BufferedReader reader = new BufferedReader(new FileReader(args));
            String line = reader.readLine(); //파일로부터 한 줄 읽어 String으로 변환
            String [] graphSize = line.split(" "); //split을 이용해 문자열을 구분자로 분해해 배열에 저장
            size = Integer.parseInt(graphSize[0]); //엣지 사이즈는 graphSize[1]에
            edgeSize = Integer.parseInt(graphSize[1]);
            adjacency=new float[size][size]; //가중치 그래프를 위한 인접 행렬

            for(int i=0; i<size;i++) {
                for(int j=0; j<size;j++) {
                    adjacency[i][j] = Integer.MAX_VALUE; //이어지지 않은 부분은 무한대 값을 넣어둔다
                }
            }

            line = reader.readLine(); //한 줄 읽으면 다음 줄로 커서가 넘어가므로 line에 또 파일의 한 줄을 읽어 String으로 변환한다

            while(line!=null) { //line이 null이 아니라면
                String listSplit[]=line.split(" "); //문자열을 구분자로 분해해 배열에 저장
                e.v = Integer.parseInt(listSplit[0]);
                e.w = Integer.parseInt(listSplit[1]);
                e.weight = Integer.parseInt(listSplit[2]);

                add(e.v,e.w,e.weight);
                line = reader.readLine(); //다시 다음줄로!

            }


            reader.close(); //파일닫기

        }catch(IOException e) {System.out.println(e);}


    } //Graph 생성자 끝


    public void prim(int v) { //들어온 int v값을 시작 정점으로
        Object [] near=new Object[size]; /*각 정점에 대해서 TV에 속해있는 정점들 중 가장 가까운 정점을 나타내는 배열 N을 넣기 위해 object타입으로 선언했다. */
        boolean [] include= new boolean[size];; /* TV를 표현한 boolean 배열 */

        float minWeight=Integer.MAX_VALUE;//min값을 저장할 변수에 무한대 값을 넣어 초기화 해둔다
/** Organizing Data // Replace Type Code with Class **/
        int n = size;
        int minIndex=v;
        int selectEdgeCount=0; //선택된 간선의 갯수를 count하기 위해서 선언한 변수이다
        int index=v;
        float sum =0;


        for(int i=0; i<size; i++) {
/**  Composing methods // Extract method  **/
            near[i]=-1; //near은 초기에 -1값으로 초기화
        }

        if(size==1) {
            System.out.println("정점이 하나 이므로 신장트리는 "+v+"입니다.");
        }
        else {

            if(edgeSize>=n-1) {

                include[v]=true;
                near[v]="N";
                System.out.println("< 신장트리 > ");
                System.out.println("시작정점 : "+v);

                while(selectEdgeCount<n-1) {
                    /*
                     * egde가 사이즈-1개 선택될 때 까지 while문을 반복한다
                     * 그리고 for문을 돌면서 포함된 정점의 인덱스를 찾고
                     * 찾은 인덱스와 인접한 정점을 가중치 행렬 ajdcency에서 찾는다 (for문 사용)
                     * 그 다음 가중치값이 있고, 포함되지 않은 정점이면 near에 연결되었다는 표시로 찾은 인덱스에 이미 포함되어 있는 정점의 인덱스값을 넣어준다
                     * 만약 지금까지의 조건을 거쳐 선택된 간선의 가중치가 원래 가지고 있던 minWeight보다 작다면 minWeight값을 갱신해준다
                     * 선택된 인덱스들은 나중에 선택되었다고 출력할 때 필요하기 때문에 minIndex와 index에 저장해둔다
                     */

                    for(int j=0; j<size; j++) {
                        if(include[j]) {
                            for(int k=0;k<size;k++) {
/**  Composing methods // Extract Variable  **/
                                if(adjacency[j][k]!=Integer.MAX_VALUE&&adjacency[j][k]>0&&!include[k]) {
                                    near[k]=j;
/**  Composing methods // Extract Variable  **/
                                    if(adjacency[j][k]<minWeight) {
                                        minWeight = adjacency[j][k];
                                        minIndex=k;
                                        index = j;
                                    }
                                }
                            } //for(k) 종료 포함된 정점들 중에서 가중치 값이 가장 작은 간선을 탐색
                        }
                    } //for(j) 종료

//가장 작은 간선을 찾아 탐색을 끝냈다면 선택할 차례

                    include[minIndex]=true; //선택된 정점은 include에서 true로 바꿔준다
                    near[minIndex]="N"; //선택된 정점은 N을 저장해준다
                    System.out.println(index+" - "+minIndex+" 가중치 : "+minWeight); //출력
                    selectEdgeCount++;
                    sum = sum+minWeight;//선택된 가중치들의 합을 출력해주기 위해 선언한 sum함수에 선택된 가중치 값을 더해주며 갱신한다
                    minWeight=Integer.MAX_VALUE; //minWeight는 다음 while루프를 위해 다시 무한대 값으로 초기화

                }//while(selectEdgeCount<=n-1)


            }
            else {
                System.out.println("간선의 갯수가 n-1개보다 작기 때문에 신장트리를 만들 수 없습니다.");
            }
            System.out.println("가중치 합 : "+sum);
        } //else 끝




    }//prim 알고리즘 끝




}
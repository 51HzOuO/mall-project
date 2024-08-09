public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                for (int k = 1; k <= 100; k++) {
                    if (k % 3 != 0) continue;
                    int ans = i * 5 + j * 3 + k / 3;
                    if (ans == 100 && i + j + k == 100) {
                        System.out.println(i + " " + j + " " + k);
                    }
                }
            }
        }
    }
}

package logic.map;

public class SeaPath {

      private final BaseRegion lr1;
      private final BaseRegion lr2;

      public SeaPath(BaseRegion lr1, BaseRegion lr2) {
            this.lr1 = lr1;
            this.lr2 = lr2;
      }

      public BaseRegion getRegion1() {
            return lr1;
      }

      public BaseRegion getRegion2() {
            return lr2;
      }
}

package kr.co.paywith.pw.common;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * 지도 관련 유틸
 */
@Component
public class GeoLocationUtil {

  /**
   * 위도 경도 정보를 가지고 DB에 저장할 위치(지점) 정보를 만든다.
   *
   * @param lat 위도
   * @param lng 경도
   * @return 유효한 위도 경도이면 위치 값, 아니면 null
   */
  public static Geometry makeGeometry(Double lat, Double lng) {
    if (lat == null || lng == null) {
      return null;
    }
    Coordinate center = new Coordinate(lat, lng);
    GeometryFactory factory = new GeometryFactory();
    Geometry geo = factory.createPoint(center);
    geo.setSRID(4326);
    return geo;
  }
}

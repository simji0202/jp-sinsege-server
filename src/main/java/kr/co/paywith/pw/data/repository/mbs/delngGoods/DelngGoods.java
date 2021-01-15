//package kr.co.paywith.pw.data.repository.mbs.delngGoods;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.EntityListeners;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import kr.co.paywith.pw.data.repository.mbs.cpn.Cpn;
//import kr.co.paywith.pw.data.repository.mbs.delng.Delng;
//import kr.co.paywith.pw.data.repository.mbs.delngGoodsOpt.DelngGoodsOpt;
//import kr.co.paywith.pw.data.repository.mbs.gcct.Gcct;
//import kr.co.paywith.pw.data.repository.mbs.goods.Goods;
//import lombok.AllArgsConstructor;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
///**
// * 거래 상품
// */
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(of = "id")
//@Getter
//@Setter
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//@ToString(exclude = {"delng"})
//public class DelngGoods {
//
//    /**
//     * 거래 상품 일련번호
//     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    /**
//     * 쿠폰 금액 제외하고 결제할 금액
//     */
//    private Integer delngAmt;
//
//    /**
//     * 쿠폰 적용 금액. (goodsAmt * goodsCnt) - cpnAmt 만큼 결제가 되어야 한다.
//     *
//     * 매장에서 보내는게 아니라면, 조작을 막기 위해 cpn의 할인율과 금액, goods를 확인해서 계산 후 서버에서 입력한다.
//     */
//    private Integer cpnAmt;
//
//    @OneToMany(mappedBy = "delngGoods", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
//    private List<DelngGoodsOpt> delngGoodsOptList = new ArrayList<>();
//
//    /**
//     * 거래 상품 수량
//     */
//    private Integer goodsCnt;
//
//
//
//
//}

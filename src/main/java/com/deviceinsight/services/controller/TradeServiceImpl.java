package com.deviceinsight.services.controller;

import com.deviceinsight.services.model.Servicequeue;
import com.deviceinsight.services.model.ServicequeuesDAO;
import com.deviceinsight.services.model.TopicItem;
import com.deviceinsight.services.websocket.Portfolio;
import com.deviceinsight.services.websocket.PortfolioPosition;
import com.deviceinsight.services.websocket.PortfolioService;
import com.deviceinsight.services.websocket.TopicItemDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Controller
public class TradeServiceImpl implements TradeService {


    private static final Log logger = LogFactory.getLog(TradeServiceImpl.class);
    public static int i = 0;
    private static CopyOnWriteArrayList<TopicItem> jj = new CopyOnWriteArrayList<TopicItem>();
    private final SimpMessageSendingOperations messagingTemplate;
    private final PortfolioService portfolioService;
    private final List<TradeResult> tradeResults = new CopyOnWriteArrayList<>();
    @Autowired
    private ServicequeuesDAO servicequeueDAO;

    @Autowired
    private TopicItemDAO productDAO;


    @Autowired
    public TradeServiceImpl(SimpMessageSendingOperations messagingTemplate, PortfolioService portfolioService) {
        this.messagingTemplate = messagingTemplate;
        this.portfolioService = portfolioService;
    }

    public String currentDateTime() {
        GregorianCalendar gc = new GregorianCalendar();

        SimpleDateFormat format_time_now1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        return format_time_now1.format(gc.getTime());
    }

    /**
     * In real application a trade is probably executed in an external system,
     * i.e. asynchronously.
     */
    public void executeTrade(Trade trade) {
        String tticker = "1";
        String tnewprice = "121212";
        String tnewbidder = "12121212";
        Portfolio portfolio = this.portfolioService.findPortfolio("fabrice");
        String ticker = trade.getTicker();
        int sharesToTrade = trade.getShares();
        PortfolioPosition newPosition = null;
        String toBeAdded = "{\"company\":\"<img style=\\\"margin-right:15px; width:110px\\\" src=\\\"/assets/no-product-photo.png\\\" />&nbsp;John\",\"ticker\":\"bbbbbbbb-970a-4c37-8c6e-9850b43a9f31\",\"price\":44.439998626708984,\"shares\":\"<div id=\\\"expireMessage_32c9effc-d158-4cbb-ad2a-b8720f716607\\\"></div>\",\"updateTime\":1451933885198,\"lastBidder\":\"\"}";
        String resres = "";
        resres += "[";
        List<PortfolioPosition> k = portfolio.getPositions();
        String persisted = "";
        int io = 0;
        int size = k.size();
        for (PortfolioPosition ll : k) {
            io++;
            String tcomp = ll.getCompany();
            String details = tcomp.replace("\"", "\\\"");
            TopicItem p1m = productDAO.findByTicker(ll.getTicker());
            persisted += "{\"company\":\"" + details + "\",\"ticker\":\"" + ll.getTicker() + "\",\"price\":44.439998626708984,\"shares\":\"<div id=\\\"expireMessage_32c9effc-d158-4cbb-ad2a-b8720f716607\\\"></div>\",\"updateTime\":1451933885198,\"lastBidder\":\"" + servicequeueDAO.renderByServicesession(p1m.getId()) + "\"}";
            if (size != io) {
                persisted += ",";
            }
        }
        resres += persisted;
        resres += "]";
        messagingTemplate.convertAndSend("/app/positions", resres);
        TopicItem product = productDAO.findByTicker(ticker);
    }

    public void clearTrade(String ticker) {
        TopicItem p0 = productDAO.findByTicker(ticker);
        messagingTemplate.convertAndSend("/topic/price.stock." + ticker,
                "{\"ticker\":\"" + ticker + "\",\"price\":" + p0.getCredit() + ",\"lastBidder\":\""
                        + "" + "\"" + ",\"isFinished\":\"" + p0.getFinished() + "\"" + "} ");
        p0.setLast_bidder("");
        TopicItem po = productDAO.findByTicker(ticker);
        String pId = po.getIdentifier();
        List<Servicequeue> lpro = servicequeueDAO.findByServicesession(po.getId());
        for (Servicequeue k : lpro) {
            servicequeueDAO.delete(k);
        }
        PortfolioPosition ppa =
                new PortfolioPosition(
                        "<img style=\"width:110px\" src=\"" + p0.getImagePath() + "\" />"
                                + "&nbsp;" + p0.getTitle(),
                        p0.getIdentifier(), new Double("" + p0.getCredit()),
                        "<div id=\"expireMessage_" + p0.getIdentifier() + "\"></div>",
                        p0.getLast_bidder());
        portfolioService.getObjjj(ppa);
        productDAO.saveOrUpdate(p0);
        portfolioService.delete(ticker);
        TopicItem deleteProduct = productDAO.findByTicker(ticker);
        productDAO.delete(deleteProduct);
        Map<String, String> params;
        RestTemplate restTemplate;
        String uri;
        uri = "http://localhost:8080/222";
        params = new HashMap<String, String>();
        restTemplate = new RestTemplate();
        restTemplate.getForObject(uri, String.class, params);
    }

    public void sendTradeNotificationsOriginal() {
    }

    public int getRandomNumber(int pMaximum) {
        return (int) ((Math.random() * pMaximum) + 1);
    }

    private static class TradeResult {
        private final String user;
        private final PortfolioPosition position;

        public TradeResult(String user, PortfolioPosition position) {
            this.user = user;
            this.position = position;
        }
    }

}

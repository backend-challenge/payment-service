package com.invillia.acme.scheduling;

import com.invillia.acme.model.db.Payment;
import com.invillia.acme.model.dto.PaymentStatus;
import com.invillia.acme.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RefundedJob {

	private static final Logger log = LoggerFactory.getLogger(RefundedJob.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	@Autowired
	private PaymentRepository repository;

	@Scheduled(cron = "0 10 10	 * * ?")
	public void reportCurrentTime() {

		List<Payment> paymentList = repository.findAll()
				.stream()
				.filter(x ->
						PaymentStatus.PENDING_REFUNDED.equals(x.getStatus()) &&
								LocalDate.now().isAfter(x.getPaymentDate().plusDays(10))

				)
				.peek(x -> x.setStatus(PaymentStatus.REFUNDED))
				.collect(Collectors.toList());
		repository.saveAll(paymentList);
		log.info("Rodando a rotina de reembolso", dateFormat.format(new Date()));
	}
}


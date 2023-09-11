package com.reshet.sut.app

import io.micrometer.core.annotation.Timed
import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@SpringBootApplication
class AppApplication

fun main(args: Array<String>) {
	runApplication<AppApplication>(*args)
}

@Configuration
@EnableAspectJAutoProxy
class AutoTimingConfiguration {
	@Bean
	fun timedAspect(registry: MeterRegistry): TimedAspect {
		return TimedAspect(registry)
	}
}

@RestController
class ReadControllers(
	private val meterRegistry: MeterRegistry
) {
	private val random = Random()
	@GetMapping("/read")
	@Timed(histogram = true, percentiles = [0.5, 0.75, 0.95, 0.99, 0.9999])
	fun read(): String {
		val counter: Counter = Counter.builder("api_read_get")
			.description("a number of requests to /read endpoint")
			.register(meterRegistry)
		counter.increment()
		Thread.sleep(10 + random.nextLong(50))
		return "Done"
	}
}
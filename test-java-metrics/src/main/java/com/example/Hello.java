package com.example;

import com.codahale.metrics.*;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;

import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.concurrent.TimeUnit;


public class Hello {
  // Create registry for Dropwizard metrics.
  static final MetricRegistry metrics = new MetricRegistry();
  // Create a Dropwizard counter.
  static final Counter counter = metrics.counter("my_example_counter_total");
  static final Timer timer = metrics.timer("my_example_timer_total");
  public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

  public static void main( String[] args ) throws Exception {
      // Increment the counter.
      counter.inc();
      final Timer.Context context1 = timer.time();
      Thread.sleep(500);
      context1.stop();

      // Hook the Dropwizard registry into the Prometheus registry
      // via the DropwizardExports collector.
      CollectorRegistry.defaultRegistry.register(new DropwizardExports(metrics));

      final ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(metrics)
				.convertRatesTo(TIME_UNIT)
				.convertDurationsTo(TIME_UNIT)
				.build();
		consoleReporter.start(5, TimeUnit.SECONDS);

      // Expose Prometheus metrics.
      Server server = new Server(1234);
      ServletContextHandler context = new ServletContextHandler();
      context.setContextPath("/");
      server.setHandler(context);
      context.addServlet(new ServletHolder(new MetricsServlet()), "/metrics");
      // Add metrics about CPU, JVM memory etc.
      DefaultExports.initialize();
      // Start the webserver.
      server.start();
      server.join();
  }
}

package org.springframework.hateoas.examples;

import static org.springframework.hateoas.examples.OrderStatus.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

/**
 * A {@link RepresentationModelProcessor} that takes an {@link Order} that has been wrapped by Spring Data REST into an
 * {@link EntityModel} and applies custom Spring HATEAOS-based {@link Link}s based on the state.
 *
 */
@Component
public class OrderProcessor implements RepresentationModelProcessor<EntityModel<Order>> {

	private final RepositoryRestConfiguration configuration;

	public OrderProcessor(RepositoryRestConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	public EntityModel<Order> process(EntityModel<Order> model) {

		CustomOrderController controller = methodOn(CustomOrderController.class);
		String basePath = configuration.getBasePath().toString();

		// If PAID_FOR is valid, add a link to the `pay()` method
		if (valid(model.getContent().getOrderStatus(), OrderStatus.PAID_FOR)) {
			model.add(applyBasePath( //
					linkTo(controller.pay(model.getContent().getId())) //
							.withRel(IanaLinkRelations.PAYMENT), //
					basePath));
		}

		// If CANCELLED is valid, add a link to the `cancel()` method
		if (valid(model.getContent().getOrderStatus(), OrderStatus.CANCELLED)) {
			model.add(applyBasePath( //
					linkTo(controller.cancel(model.getContent().getId())) //
							.withRel(LinkRelation.of("cancel")), //
					basePath));
		}

		// If FULFILLED is valid, add a link to the `fulfill()` method
		if (valid(model.getContent().getOrderStatus(), OrderStatus.FULFILLED)) {
			model.add(applyBasePath( //
					linkTo(controller.fulfill(model.getContent().getId())) //
							.withRel(LinkRelation.of("fulfill")), //
					basePath));
		}

		return model;
	}

	/**
	 * Adjust the {@link Link} such that it starts at {@literal basePath}.
	 *
	 * @param link - link presumably supplied via Spring HATEOAS
	 * @param basePath - base path provided by Spring Data REST
	 * @return new {@link Link} with these two values melded together
	 */
	private static Link applyBasePath(Link link, String basePath) {

		URI uri = link.toUri();

		URI newUri = null;
		try {
			newUri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), //
					uri.getPort(), basePath + uri.getPath(), uri.getQuery(), uri.getFragment());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return new Link(newUri.toString(), link.getRel());
	}
}

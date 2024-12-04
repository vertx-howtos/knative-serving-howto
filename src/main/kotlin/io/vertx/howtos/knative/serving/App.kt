package io.vertx.howtos.knative.serving

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import org.asciidoctor.Asciidoctor
import org.asciidoctor.Options
import org.asciidoctor.SafeMode

fun main() {
  val vertx = Vertx.vertx() // <1>

  val asciidoctor = Asciidoctor.Factory.create()  // <2>
  val options = Options.builder()
    .safe(SafeMode.SAFE)
    .backend("html5")
    .standalone(true)
    .build()

  val router = Router.router(vertx)
  router.route().handler(BodyHandler.create())  // <3>

  router.post().handler { ctx ->  // <4>
    ctx.response()
      .putHeader("Content-Type", "text/html")
      .end(asciidoctor.convert(ctx.body().asString(), options))
  }

  vertx.createHttpServer()
    .requestHandler(router)
    .listen(8080)
    .await()
}

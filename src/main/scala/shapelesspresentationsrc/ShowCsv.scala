package shapelesspresentationsrc

import simulacrum._

import shapeless._


@typeclass trait ShowCsv[A] {
  def showCsv(a: A): String
}

object ShowCsv {
  implicit def showHConsNil[H: ShowCsv]: ShowCsv[H :: HNil] =
    l => s"${ShowCsv[H].showCsv(l.head)}"

  implicit def showHCons[H: ShowCsv, T <: HList: ShowCsv]: ShowCsv[H :: T] =
    l => s"${ShowCsv[H].showCsv(l.head)},${ShowCsv[T].showCsv(l.tail)}"

  implicit def showCCons[H: ShowCsv, T <: Coproduct: ShowCsv]: ShowCsv[H :+: T] =
    c => c.eliminate(ShowCsv[H].showCsv, ShowCsv[T].showCsv)

  implicit def showCNil: ShowCsv[CNil] = _ => ""


  implicit def showGen[A, Repr](implicit lg: Generic.Aux[A, Repr], sh: Lazy[ShowCsv[Repr]]): ShowCsv[A] =
    a => sh.value.showCsv(lg.to(a))



  implicit val showStr   : ShowCsv[String] = identity
  implicit val showLong  : ShowCsv[Long  ] = _.toString
  implicit val showDouble: ShowCsv[Double] = _.toString

  implicit def showList[A: ShowCsv]: ShowCsv[List[A]] = _.map(ShowCsv[A].showCsv).mkString("\n")
}

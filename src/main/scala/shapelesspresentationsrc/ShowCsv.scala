package shapelesspresentationsrc

import simulacrum._
import shapeless._


@typeclass trait ShowCsv[A] {
  def showCsv(a: A): String
}

object ShowCsv {
  // Instances for HList: product types (case classes).
  implicit def showHConsNil[H: ShowCsv]: ShowCsv[H :: HNil] =
    l => s"${ShowCsv[H].showCsv(l.head)}"

  implicit def showHCons[H: ShowCsv, T <: HList: ShowCsv]: ShowCsv[H :: T] =
    l => s"${ShowCsv[H].showCsv(l.head)},${ShowCsv[T].showCsv(l.tail)}"

  // Instances for Coproduct: superclasses.
  implicit def showCNil: ShowCsv[CNil] =
    _.impossible

  implicit def showCCons[H: ShowCsv, T <: Coproduct: ShowCsv]: ShowCsv[H :+: T] =
    _.eliminate(ShowCsv[H].showCsv, ShowCsv[T].showCsv)

  // Instances for everything that can be converted to something that
  // has an instance to it.
  //
  // That is, this is a type class for case classes and traits that does the job
  // by converting them to HLists or Coproducts first and then converting these
  // to CSV.
  implicit def showGen[A, Repr](implicit lg: Generic.Aux[A, Repr], sh: Lazy[ShowCsv[Repr]]): ShowCsv[A] =
    a => sh.value.showCsv(lg.to(a))


  // Instances for other types.
  implicit val showStr   : ShowCsv[String] = identity
  implicit val showLong  : ShowCsv[Long  ] = _.toString
  implicit val showDouble: ShowCsv[Double] = _.toString

  implicit def showList[A: ShowCsv]: ShowCsv[List[A]] = _.map(ShowCsv[A].showCsv).mkString("\n")
}

export interface IConceptIncome {
  id?: number;
  name?: string;
  keyConcept?: string;
}

export class ConceptIncome implements IConceptIncome {
  constructor(public id?: number, public name?: string, public keyConcept?: string) {}
}

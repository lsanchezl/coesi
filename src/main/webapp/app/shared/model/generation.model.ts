export interface IGeneration {
  id?: number;
  name?: string;
}

export class Generation implements IGeneration {
  constructor(public id?: number, public name?: string) {}
}

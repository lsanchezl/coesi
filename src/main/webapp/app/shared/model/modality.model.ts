export interface IModality {
  id?: number;
  name?: string;
  description?: string;
}

export class Modality implements IModality {
  constructor(public id?: number, public name?: string, public description?: string) {}
}

export interface ICareer {
  id?: number;
  name?: string;
}

export class Career implements ICareer {
  constructor(
    public id?: number,
    public name?: string,
  ) {}
}

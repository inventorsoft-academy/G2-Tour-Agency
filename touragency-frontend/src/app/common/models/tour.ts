export class Tour {
  id: number;
  destination: string;
  country: string;
  startDate;
  endDate;
  tourType: string;
  capacity: number;
  price: number;
  agency: string;
  isActive: boolean;

  public active(): boolean {
    return this.isActive;
  }
}

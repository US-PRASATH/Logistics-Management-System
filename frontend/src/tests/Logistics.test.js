import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import React from 'react';
import OrderList from '../components/Order/OrderList';
import ShipmentList from '../components/Shipment/ShipmentList';
import SupplierList from '../components/Supplier/SupplierList';
import TransportPlanList from '../components/TransportPlan/TransportPlanList';

// Mocking individual API service functions
jest.mock('../api/orderService', () => ({
  getOrders: jest.fn(),
  deleteOrder: jest.fn(),
}));

jest.mock('../api/shipmentService', () => ({
  getAllShipments: jest.fn(),
  deleteShipment: jest.fn(),
}));

jest.mock('../api/supplierService', () => ({
  getAllSuppliers: jest.fn(),
  deleteSupplier: jest.fn(),
}));

jest.mock('../api/WarehouseItemService', () => ({
  getAllWarehouseItems: jest.fn(),
  deleteWarehouseItem: jest.fn(),
}));

jest.mock('../api/TransportPlanService', () => ({
  getAllTransportPlans: jest.fn(),
  deleteTransportPlan: jest.fn(),
}));

describe('Logistics Management System Components', () => {

    // Test cases for OrderList
    describe('OrderList Component', () => {
        beforeEach(() => {
            require('../api/orderService').getOrders.mockResolvedValue({ data: [{ id: 1, product: 'Test Item', quantity: 10 }] });
        });

        // test('renders order list', async () => {
        //     render(<OrderList />);
        //     await waitFor(() => expect(screen.getByText(/Test Item/)).toBeInTheDocument());
        // });

        test('deletes an order', async () => {
            require('../api/orderService').deleteOrder.mockResolvedValue({});
            render(<OrderList />);
            await waitFor(() => fireEvent.click(screen.getByText(/Delete/)));
            expect(require('../api/orderService').deleteOrder).toHaveBeenCalledWith(1);
        });
    });

    // Test cases for ShipmentList
    describe('ShipmentList Component', () => {
        beforeEach(() => {
            require('../api/shipmentService').getAllShipments.mockResolvedValue({ data: [{ id: 1, trackingNumber: '123', status: 'In Transit' }] });
        });

        test('renders shipments list', async () => {
            render(<ShipmentList />);
            await waitFor(() => expect(screen.getByText(/123/)).toBeInTheDocument());
        });

        test('deletes a shipment', async () => {
            require('../api/shipmentService').deleteShipment.mockResolvedValue({});
            render(<ShipmentList />);
            await waitFor(() => fireEvent.click(screen.getByText(/Delete/)));
            expect(require('../api/shipmentService').deleteShipment).toHaveBeenCalledWith(1);
        });
    });

    // Test cases for SupplierList
    describe('SupplierList Component', () => {
        beforeEach(() => {
            require('../api/supplierService').getAllSuppliers.mockResolvedValue({ data: [{ id: 1, name: 'Test Supplier', performanceRating: 'Excellent' }] });
        });

        test('renders supplier list', async () => {
            render(<SupplierList />);
            await waitFor(() => expect(screen.getByText(/Test Supplier/)).toBeInTheDocument());
        });

        test('deletes a supplier', async () => {
            require('../api/supplierService').deleteSupplier.mockResolvedValue({});
            render(<SupplierList />);
            await waitFor(() => fireEvent.click(screen.getByText(/Delete/)));
            expect(require('../api/supplierService').deleteSupplier).toHaveBeenCalledWith(1);
        });
    });

    // Test cases for WarehouseList
    // describe('WarehouseList Component', () => {
    //     beforeEach(() => {
    //         require('../api/WarehouseItemService').getAllWarehouseItems.mockResolvedValue({ data: [{ id: 1, itemName: 'Item A', quantity: 50 }] });
    //     });

    //     test('renders warehouse items list', async () => {
    //         render(<WarehouseItemList />);
    //         await waitFor(() => expect(screen.getByText(/Item A/)).toBeInTheDocument());
    //     });

    //     test('deletes a warehouse item', async () => {
    //         require('../api/WarehouseItemService').deleteWarehouseItem.mockResolvedValue({});
    //         render(<WarehouseItemList />);
    //         await waitFor(() => fireEvent.click(screen.getByText(/Delete/)));
    //         expect(require('../api/WarehouseItemService').deleteWarehouseItem).toHaveBeenCalledWith(1);
    //     });
    // });

    // Test cases for TransportPlanList
    describe('TransportPlanList Component', () => {
        beforeEach(() => {
            require('../api/TransportPlanService').getAllTransportPlans.mockResolvedValue({ data: [{ id: 1, route: 'Route 101', carrier: 'Carrier X' }] });
        });

        test('renders transport plans list', async () => {
            render(<TransportPlanList />);
            await waitFor(() => expect(screen.getByText(/Route 101/)).toBeInTheDocument());
        });

        test('deletes a transport plan', async () => {
            require('../api/TransportPlanService').deleteTransportPlan.mockResolvedValue({});
            render(<TransportPlanList />);
            await waitFor(() => fireEvent.click(screen.getByText(/Delete/)));
            expect(require('../api/TransportPlanService').deleteTransportPlan).toHaveBeenCalledWith(1);
        });
    });
});

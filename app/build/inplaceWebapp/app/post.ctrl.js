(function () {
    'use strict';

    angular.module('app').controller('PostCtrl', Ctrl);

    function Ctrl(http, messageService) {

        const vm = this;
        vm.insertNew = insertNew;
        vm.deleteOrder = deleteOrder;

        vm.newOrder = {};
        vm.orders = [];
        vm.errors = [];

        init();

        function init() {
            http.get('api/orders').then(function (data) {
                vm.orders = data;
                vm.errors = [];
            });
        }

        function insertNew() {
            http.post('api/orders', vm.newOrder).then(function () {
                vm.newOrder = {};
                init();
            }, errorHandler);
        }

        function deleteOrder(orderId) {
            http.delete('api/orders/' + orderId).then(function () {
                init();
            }, errorHandler);
        }

        function errorHandler(response) {
            if (response.data.errors) {
                vm.errors = response.data.errors.map(function (error) {

                    console.log(error.code);

                    return messageService.getMessage(error.code, error.arguments);
                });
            } else {
                console.log('Error: ' + JSON.stringify(response.data));
            }
        }
    }

})();

(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FormsPatientController', FormsPatientController);

    FormsPatientController.$inject = [ 'TypePatientProcess', 'chart', '$state', 'Forms', 'Evaluation',
        'ChartToForm', '$uibModal', '$filter', 'listPatientProcess', '$sessionStorage' ];

    function FormsPatientController ( TypePatientProcess, chart, $state, Forms, Evaluation,
                                      ChartToForm, $uibModal, $filter, listPatientProcess, $sessionStorage ) {
        var vm = this;

        //Functions
        vm.backDetailsBasic = backDetailsBasic;
        vm.addNew = addNew;
        vm.loadList = loadList;
        vm.loadStatusColor = loadStatusColor;
        vm.newForm = newForm;
        vm.search = search;
        vm.consentInvoice = consentInvoice;
        vm.evaluationInvoice = evaluationInvoice;
        vm.deleteConsent = deleteConsent;
        vm.deleteEvaluation = deleteEvaluation;
        vm.evaluationInvoiceView = evaluationInvoiceView;

        //Variable
        vm.patientProcessForms = listPatientProcess;
        vm.listForms = [];
        vm.listFormsCopy = [];
        // vm.countPending = 0;
        // vm.countInProcess = 0;
        // vm.countFinalized = 0;

        vm.statusTypes = ['default', 'danger', 'primary', 'success', 'info', 'warning'];

        vm.activeTab = $sessionStorage.formTab?$sessionStorage.formTab:0;

        loadAll();
        function loadAll() {
            vm.processTab = listPatientProcess[vm.activeTab];
            loadList(vm.processTab);
        }

        function backDetailsBasic() {
            $state.go('patient', {}, {reload: true});
        }

        function addNew() {

        }

        function loadList(item) {
            if (!vm.searchQuery) {
                vm.listForms = [];
                if (item) {
                    Forms.allFormsByChart({chId: chart.id, ppId: item.id}, function (result) {
                        if (result.length > 0)
                            vm.listForms = result;
                        angular.copy(result, vm.listFormsCopy);
                    });
                } else {
                    vm.listForms = vm.listFormsCopy;
                }
            }else{
                vm.listForms = $filter('filter')(vm.listFormsCopy, vm.searchQuery, undefined);
            }
            $sessionStorage.formTab = vm.activeTab;
            $sessionStorage.patientProcess = item;
        }

        function prepareForms(event, result) {
            vm.countPending = 0;
            vm.countInProcess = 0;
            vm.countFinalized = 0;

            if(vm.listForms.length){
                for (var i = 0; i < vm.listForms.length; i++){
                    switch(vm.listForms[i].status) {
                        case 'Pending':
                            vm.countPending++;
                            break
                        case 'InProcess':
                            vm.countInProcess++;
                            break
                        default :
                            break;
                    }
                }
            }
            angular.copy(vm.listForms, vm.listFormsCopy);
        }


        function search() {
            vm.loadList();
        }

        function loadStatusColor(value) {
            if(value){
                switch(value) {
                    case 'Pending':
                        return 'danger';
                    case 'InProcess':
                        return 'warning';
                    case 'Finalized':
                        return 'success';
                    default :
                        return 'default';
                }
            }
        }

        var modalFormInstance = null;

        function newForm(ppId) {
            if (modalFormInstance !== null) return;
            modalFormInstance = $uibModal.open({
                templateUrl: 'app/entities/forms/new/assign-form.html',
                controller: 'AssignFormController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    patientProcess: ['TypePatientProcess', function(TypePatientProcess) {
                        return TypePatientProcess.get({id : ppId}).$promise;
                    }],
                    listForms: [function () {
                        return vm.listForms;
                    }],
                    levelCare: [function () {
                        if (!chart.waitingRoom) {
                            return chart.typeLevelCare?chart.typeLevelCare.id: null
                        }
                    }]
                }
            });
            modalFormInstance.result.then(
                resetModalForm,
                resetModalForm
            );
        }

        var resetModalForm = function (result) {
            modalFormInstance = null;
            if(result)
                $state.go('patient', {}, { reload: 'patient' });
            // else
            //     $state.go('patient', {}, { reload: 'patient' });
        };

        function consentInvoice(consentId, index) {
            $sessionStorage.typeForm = 'consent';
            $sessionStorage.indexSelected = index;
            $sessionStorage.consentId = consentId;
            $state.go('form-invoice');
        }

        function evaluationInvoice(evaluationId, index) {
            $sessionStorage.typeForm =  'evaluation';
            $sessionStorage.indexSelected = index;
            $sessionStorage.evaluationId = evaluationId;
            $state.go('evaluation-invoice');
        }

        function evaluationInvoiceView(evaluationId) {
            $sessionStorage.typeForm =  'evaluation';
            $state.go('evaluation-viewinvoice', {eid: evaluationId}, { reload: true });
        }

        //Modals for delete Forms(Consent, Evaluation)


        var modalInstance = null;

        function deleteConsent(id) {
            if (modalInstance !== null) return;
            modalInstance = $uibModal.open({
                animation: true,
                backdrop: 'static',
                keyboard: false,
                templateUrl: 'app/entities/forms/delete/consent-delete-dialog.html',
                controller: 'ConsentDeleteController',
                controllerAs: 'vm',
                size: 'sm',
                resolve: {
                    entity: ['ChartToForm', function(ChartToForm) {
                        return ChartToForm.get({id : id}).$promise;
                    }]
                }
            });
            modalInstance.result.then(
                resetModalDelete,
                resetModalDelete
            );
        }

        function deleteEvaluation(id) {
            if (modalInstance !== null) return;
            modalInstance = $uibModal.open({
                animation: true,
                backdrop: 'static',
                keyboard: false,
                templateUrl: 'app/entities/forms/delete/evaluation-delete-dialog.html',
                controller: 'EvaluationDeleteController',
                controllerAs: 'vm',
                size: 'sm',
                resolve: {
                    entity: ['Evaluation', function(Evaluation) {
                        return Evaluation.get({id : id}).$promise;
                    }]
                }
            });
            modalInstance.result.then(
                resetModalDelete,
                resetModalDelete
            );
        }

        var resetModalDelete = function (id) {
            modalInstance = null;
            if(id)
                $state.go('patient', {fid: id} , {reload: 'patient'});
        };

    }
})();

(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationSendFaxDialogController', PatientMedicationSendFaxDialogController);

    PatientMedicationSendFaxDialogController.$inject = ['Base64', '$localStorage', '$sessionStorage', '$uibModalInstance', 'patientMedication', 'PatientMedication', 'ServiceProvider', 'Employee'];

    function PatientMedicationSendFaxDialogController(Base64, $localStorage, $sessionStorage, $uibModalInstance, patientMedication, PatientMedication, ServiceProvider, Employee) {
        var vm = this;

        vm.patientMedication = patientMedication;

        console.log(vm.patientMedication);
        vm.serviceProviders = ServiceProvider.query();

        vm.clear = clear;
        vm.confirmSendFax = confirmSendFax;

        vm.destination = true;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmSendFax() {
            vm.saving = true;
            var location = window.location;
            var domain = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');

            var toInfo ={};

            if(vm.destination){
               var toInfo = {
                   patientMedicationId: vm.patientMedication.id,
                   faxNumber: vm.ServiceDestination.fax,
                   serviceProviderName: vm.ServiceDestination.name,
                   domain:domain,
                   token:  Base64.encode($localStorage.authenticationToken || $sessionStorage.authenticationToken)
               };
           }else{
               var toInfo = {
                   patientMedicationId: vm.patientMedication.id,
                   faxNumber: vm.destinationFax,
                   serviceProviderName: vm.destinationName?vm.destinationName:'No Name',
                   domain:domain,
                   token:  Base64.encode($localStorage.authenticationToken || $sessionStorage.authenticationToken)
               };

           }


            PatientMedication.sendFax(toInfo, function (data) {
                console.log(data);

                if(data && data.value){
                    alert("Fax sent successfully.");
                    $uibModalInstance.dismiss('cancel');
                }else{
                    alert("Fax sent unsuccessfully. Consult the administrator.");
                    vm.saving = false;
                }
            }, function (error) {
                console.log(error);
            });


        }


    }
})();

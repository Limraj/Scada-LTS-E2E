package org.scadalts.e2e.cli.commands;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.cli.config.E2eConfigCli;
import org.scadalts.e2e.cli.options.DefaultOptions;
import org.scadalts.e2e.cli.providers.VersionProvider;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.api.E2eTestApi;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import static org.scadalts.e2e.common.utils.E2eSystemInfoUtil.printHeaderWithConfig;

@Log4j2
@Getter
@ToString
@Command(name = "run-test",
        versionProvider = VersionProvider.class,
        resourceBundle = "lang.e2e")
public class RunTestCommand extends DefaultOptions implements Runnable {

    @ToString.Exclude
    @CommandLine.ParentCommand
    private E2eCommand e2eCommand;

    @Override
    public void run() {
        E2eConfig config = E2eConfigCli.builder()
                .fromRunTest(this)
                .fromE2e(e2eCommand)
                .build();
        printHeaderWithConfig(config);
        E2eTestApi tests = E2eTestApi.newInstance();
        tests.run(config);
    }
}
